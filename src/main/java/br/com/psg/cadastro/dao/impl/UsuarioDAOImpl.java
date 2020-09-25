package br.com.psg.cadastro.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import br.com.psg.cadastro.dao.UsuarioDAO;
import br.com.psg.cadastro.model.Usuario;
import br.com.psg.cadastro.util.HibernateUtil;

public class UsuarioDAOImpl implements UsuarioDAO {

	private static UsuarioDAOImpl usuarioDAOImpl = null;

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public Long saveUsuario(Usuario usuario) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Long id = (Long) session.save(usuario);
		transaction.commit();
		session.close();

		return id;
	}
	
	public void updateUsuario(Usuario usuario) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(usuario);
		transaction.commit();
		session.close();
	}

	public void deleteUsuario(Long id) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Usuario usuario = session.get(Usuario.class, id);
		session.delete(usuario);
		transaction.commit();
		session.close();
	}

	public Usuario findUsuarioById(Long id) {
		Session session = this.sessionFactory.openSession();
		Usuario usuario = session.get(Usuario.class, id);
		session.close();
		
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listaUsuario() {
		Session session = this.sessionFactory.openSession();
		List<Usuario> listUsuario = session.createCriteria(Usuario.class).list();
		session.close();
		
		return listUsuario;
	}    

	public static UsuarioDAO getInstance() {
		if (usuarioDAOImpl == null)
			usuarioDAOImpl = new UsuarioDAOImpl();

		return usuarioDAOImpl;
	}
}