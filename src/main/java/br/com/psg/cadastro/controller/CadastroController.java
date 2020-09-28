package br.com.psg.cadastro.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import br.com.psg.cadastro.dao.UsuarioDAO;
import br.com.psg.cadastro.dao.impl.UsuarioDAOImpl;
import br.com.psg.cadastro.model.Usuario;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadastroController implements Initializable {

	
	@FXML
	private TableView<Usuario> tblCadastro;
	@FXML
	private TableColumn<Usuario, Long> clId;
	@FXML
	private TableColumn<Usuario, String> clNome;
	@FXML
	private TableColumn<Usuario, String> clCpf;
	@FXML
	private TableColumn<Usuario, Date> clData;
	@FXML
	private TextField tfNome;
	@FXML
	private TextField tfCpf;
	@FXML
	private DatePicker tdData;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnAtualizar;
	@FXML
	private Button btnApagar;
	@FXML
	private Button btnLimpar;
	
	private UsuarioDAO userdao = UsuarioDAOImpl.getInstance();

	private ObservableList<Usuario> obsList;

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		userdao = UsuarioDAOImpl.getInstance();
		configCln();
		atualizaTbl();
		configTable();
		
	}
	
	//Atualiza a table com o db//
	private void atualizaTbl() {
		List<Usuario> list = userdao.listaUsuario();
		obsList = FXCollections.observableArrayList(list);
		tblCadastro.setItems(obsList);
		
	}

	private void configCln() {
		clId.setCellValueFactory(new PropertyValueFactory<>("id"));
		clNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		clCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		//Formata objeto date para exibição na coluna//
		clData.setCellFactory(column -> {
		    TableCell<Usuario, Date> cell = new TableCell<Usuario, Date>() {
		        private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		        @Override
		        protected void updateItem(Date item, boolean empty) {
		            super.updateItem(item, empty);
		            if(empty) {
		                setText(null);
		            }
		            else {
		                setText(format.format(item));
		            }
		        }
		    };

		    return cell;
		});
		clData.setCellValueFactory(new PropertyValueFactory<>("data"));

	}
	
	@FXML
	public void btnSalvar() {
		Usuario us = new Usuario();
		setUser(us);
		userdao.saveUsuario(us);
		atualizaTbl();
		btnLimpar();

	}
	
	//Atualizar usuario//
	public void btnAtualizar() {
		Usuario us = tblCadastro.getSelectionModel().getSelectedItem();
		setUser(us);
		userdao.updateUsuario(us);
		atualizaTbl();
		btnLimpar();

	}
	
	//Deletar usuario//
	public void btnApagar() {
		Usuario us = tblCadastro.getSelectionModel().getSelectedItem();
		userdao.deleteUsuario(us.getId());
		atualizaTbl();
		btnLimpar();
		
	}
	
	//Limpa os input label//
	public void btnLimpar() {
        tblCadastro.getSelectionModel().select(null);
        tfNome.setText("");
        tfCpf.setText("");
        tdData.setValue(null);
    }
	
	//Recebe os parametros para cadastro//
	private void setUser(Usuario us) {
		us.setNome(tfNome.getText());
		us.setCpf(tfCpf.getText());
		us.setData(dataSelecionada());
	}

	//Configura seleção do datapicker//
	private Date dataSelecionada() {
		LocalDate time = tdData.getValue();
		return Date.from(time.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	private void configTable() {
		
        BooleanBinding camposPreenchidos = tfNome.textProperty().isEmpty()
                .or(tfCpf.textProperty().isEmpty())
                .or(tdData.valueProperty().isNull());
        //Verifica se há algo selecionado na tabela//
        BooleanBinding algoSelecionado = tblCadastro.getSelectionModel().selectedItemProperty().isNull();
        //Alguns botões só são habilitados se algo for selecionado na tabela//
        btnApagar.disableProperty().bind(algoSelecionado);
        btnAtualizar.disableProperty().bind(algoSelecionado);
        btnLimpar.disableProperty().bind(algoSelecionado);
        //O botão Salvar só é habilitado se todos os campos estiverem preenchidos//
        btnSalvar.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
		
		//Seleciona linha para no input para atualizar ou deletar usuario//
		tblCadastro.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
            if (n != null) {
                LocalDate data = null;
                data = n.getData().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
                tfNome.setText(n.getNome());
                tfCpf.setText(n.getCpf());
                tdData.setValue(data);
            }
        });
	}
	
}
