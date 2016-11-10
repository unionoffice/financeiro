package br.com.unionoffice.dao;

import java.io.DataInput;
import java.util.Calendar;
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

	public List<Movimento> listarTodos() {
		manager = ConnectionFactory.getManager();
		TypedQuery<Movimento> query = manager.createQuery("select m from Movimento m", Movimento.class);
		return query.getResultList();
	}

	public List<Movimento> listar() {
		manager = ConnectionFactory.getManager();
		TypedQuery<Movimento> query = manager.createQuery(
				"select m from Movimento m where m.vencimento >= :dataInicial and m.vencimento <= :dataFinal",
				Movimento.class);
		Calendar dtInicial = Calendar.getInstance();
		dtInicial.set(Calendar.HOUR_OF_DAY, 0);
		dtInicial.set(Calendar.MINUTE, 0);		
		query.setParameter("dataInicial", dtInicial);
		Calendar dtFinal = (Calendar) dtInicial.clone();
		dtFinal.set(Calendar.HOUR, 23);
		dtFinal.set(Calendar.MINUTE, 59);
		dtFinal.add(Calendar.DAY_OF_MONTH, 30);
		query.setParameter("dataFinal", dtFinal);
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

	public void atualizar(Movimento movimento) {
		manager = ConnectionFactory.getManager();
		manager.getTransaction().begin();
		manager.merge(movimento);
		manager.getTransaction().commit();
		manager.close();
	}

}
