package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;	
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
				"INSERT INTO department"
				+ "(Name) "
				+ "VALUES "
				+ "(?)");
			
			st.setString(1, obj.getName());
			
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Id = ?, Name = ? "
					+ "WHERE Id = ?");
			
			st.setInt(1, obj.getId());
			st.setString(2, obj.getName());
			st.setInt(3, obj.getId());
			
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;
		
		try {			
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if (rs.next()){				
				Department dep = instantiateDepartment(rs);
				return dep;
			}		
		
			return null;
		
		}		
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Id");
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<>();
			
			while (rs.next()){				
				Department dep = instantiateDepartment(rs);
				list.add(dep);				
			}		
		
			return list;
		
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}	
	
	// Ap�s execu��o da Query SQL, os dados s�o alocados num ResultSet.
		// O m�todo abaixo instancia um objeto e resgata os dados do ResultSet gerado pela consulta.
		//rs.getXXX -> resgate dos dados do ResultSet / xxx.setXXX() -> atribui os valor resgatados no objeto
		private Department instantiateDepartment(ResultSet rs) throws SQLException {
			Department dep = new Department(); 
			dep.setId(rs.getInt("Id"));
			dep.setName(rs.getString("Name"));
			return dep;
		}	
}
