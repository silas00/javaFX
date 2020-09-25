package br.com.psg.cadastro.dao;

import java.util.List;

import br.com.psg.cadastro.model.Usuario;

public interface UsuarioDAO {
		
		Long saveUsuario(Usuario usuario);

		Usuario findUsuarioById(Long id);
		
		void updateUsuario(Usuario usuario);

		void deleteUsuario(Long id);

		List<Usuario> listaUsuario();

}
