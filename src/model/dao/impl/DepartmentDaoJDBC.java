package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	
	//FAZ A CONEXÃO COM O BANCO DE DADOS
	public DepartmentDaoJDBC(Connection conn) {
		
		this.conn = conn;
	}


	@Override
	public void insert(Department obj) {
	PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"INSERT INTO department "
					+"(Name, Id )"
					+ "VALUES "
					+"(?,?) ",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			
			int rowsAffected = st.executeUpdate();
					
			if(rowsAffected > 0) {
				
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					
					int id = rs.getInt(1);//POPULA O OBJETO COM O NOVO ID
					obj.setId(id);
					
				}
				DB.closeResultSet(rs); //FECHA O RESULTSET
			}
			else {
				
				throw new DbException("Unexpected error! No rows affected! ");
			}
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
					"UPDATE Department  "
					+ "SET Name = ? "
					+ "WHERE Id = ? "); 
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
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
			
			st = conn.prepareStatement(
					"DELETE FROM department WHERE Id = ? ");

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
			st = conn.prepareStatement(
					"SELECT * FROM department " 
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
					
			rs = st.executeQuery();
			
			while (rs.next()) { 
				
				Department dep = DepartmentInstantiate(rs);
				
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
			
			st = conn.prepareStatement("SELECT * from department ");
			
			rs = st.executeQuery();
			
			List<Department> list = new ArrayList<Department>();
			Map<Integer, Department> map = new HashMap<>();
			
		
			while (rs.next()) { 
				
//BUSCA SE JA EXISTE DENTRO DO MAP UM DEPARTMENT COM ID REPETIDO POIS NAO ACEITA REPETIÇÕES
				Department dep = map.get(rs.getInt("Id"));
				
				if(dep == null) {
					
				//PEGA O DEPARTAMENTO E SALVA NO DEP SE ELE NAO EXISTIR(dep = null)
					dep = DepartmentInstantiate(rs);
					map.put(rs.getInt("Id"), dep);
				}
				
				
				Department obj = DepartmentInstantiate(rs);
				list.add(obj);					
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
	
	//ANTES DE TUDO INSTANCIA A CLASSE  //A CLASSE RESULTSET SÃO OS DADOS QUE VEM DO BANCO DE DADOS 
	private Department DepartmentInstantiate(ResultSet rs) throws SQLException {
		//INSTANCIANDO
		Department dep = new Department();
		
		dep.setId(rs.getInt("Id"));//SETANDO OS DADOS
		dep.setName(rs.getString("Name"));

		return dep;
		
	
		}
	
	

}
