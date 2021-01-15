package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	Connection conn = null;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department d) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department (name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, d.getName());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					d.setId(rs.getInt(1));
				}
				DB.closeResulSet(rs);
			} else {
				throw new DbException("Unexpected error, cannot be inserted!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department d) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department set name = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, d.getName());
			st.setInt(2, d.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE id = ?");
			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM department WHERE ID = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department obj = new Department(rs.getInt("id"), rs.getString("name"));
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResulSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM department";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			List<Department> departments = new ArrayList<>();
			while (rs.next()) {
				Department obj = new Department(rs.getInt("id"), rs.getString("name"));
				departments.add(obj);
			}
			return departments;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResulSet(rs);
			DB.closeStatement(st);
		}
	}

}
