package br.com.unionoffice.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.unionoffice.modelo.Movimento;

public class MovimentoDao {
	private EntityManager manager;

	public void inserir(Movimento movimento) {
		manager = ConnectionFactory.getManager();
		manager.getTransaction().begin();
		manager.persist(movimento);
		manager.getTransaction().commit();
		manager.close();
	}

	public List<Movimento> listar() {
		manager = ConnectionFactory.getManager();
		TypedQuery<Movimento> query = manager.createQuery("select m from Movimento m", Movimento.class);
		return query.getResultList();
	}

	public void excluir(Long id) {
		manager = ConnectionFactory.getManager();
		manager.getTransaction().begin();
		manager.remove(buscar(id));
		manager.getTransaction().commit();
		manager.close();
	}

	public Movimento buscar(Long id) {
		return manager.find(Movimento.class, id);
	}
}
