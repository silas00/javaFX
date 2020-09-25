package br.com.psg.cadastro.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import br.com.psg.cadastro.dao.UsuarioDAO;
import br.com.psg.cadastro.dao.impl.UsuarioDAOImpl;
import br.com.psg.cadastro.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class CadastroController <S extends RecursiveTreeObject<S>> extends TreeTableView<S> implements Initializable {

	@FXML
	private JFXTreeTableView<S> tblCadastro;
	@FXML
	private TreeTableColumn<Usuario, Long> clId;
	@FXML
	private TreeTableColumn<Usuario, String> clNome;
	@FXML
	private TreeTableColumn<Usuario, String> clCpf;
	@FXML
	private TreeTableColumn<Usuario, Date> clData;
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
	private Button btnLimpart;
	

	private UsuarioDAO userdao = UsuarioDAOImpl.getInstance();
	
	@FXML
	public void btnSalvar() {
		Usuario us = new Usuario();
		setUser(us);
		userdao.saveUsuario(us);

	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		clId.setCellValueFactory(new TreeItemPropertyValueFactory<Usuario, Long>("id"));
		clNome.setCellValueFactory(new TreeItemPropertyValueFactory<Usuario, String>("nome"));
		clCpf.setCellValueFactory(new TreeItemPropertyValueFactory<Usuario, String>("cpf"));
		clData.setCellValueFactory(new TreeItemPropertyValueFactory<Usuario, Date>("data"));

	}

	private void setUser(Usuario us) {
		us.setNome(tfNome.getText());
		us.setCpf(tfCpf.getText());
		us.setData(dataSelecionada());
	}

	private Date dataSelecionada() {
		LocalDateTime time = tdData.getValue().atStartOfDay();
		return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	
}
