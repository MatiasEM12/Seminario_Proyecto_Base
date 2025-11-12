package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Bien;

public interface Bien_VisitaDAO {

	

	void create(String codBien, String codVisita);



	void remove(Long id);
	
	void remove(String codigoVisita);


	List<Bien> findAll();

	List<Bien> findVisita(String codVisita);

	void update(String codBienNuevo, String codBienViejo, String codVisita);

}
