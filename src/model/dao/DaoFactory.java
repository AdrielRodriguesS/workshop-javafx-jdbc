package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	// esse m�todo � utilizado para criar uma interface (SellerDao) e instanciar uma implementa��o (SellerDaoJDBC)
	//� utilizado para n�o expor a implementa��o, somente expor a interface.
	public static SellerDao criateSellerDao () {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	// esse m�todo � utilizado para criar uma interface (SellerDao) e instanciar uma implementa��o (SellerDaoJDBC)
	//� utilizado para n�o expor a implementa��o, somente expor a interface.

	public static DepartmentDao criateDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
}
