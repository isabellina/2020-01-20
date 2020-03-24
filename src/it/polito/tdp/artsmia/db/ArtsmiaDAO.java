package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<String> getRuoli(){
		String sql = " select distinct  role from authorship " ; 
		List<String> ruoli = new LinkedList<String>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				String s = res.getString("role") ;
				ruoli.add(s);
			}
			return ruoli;
		}
		
		catch(SQLException s) {
			s.printStackTrace();
		}
		
		return  null;
	}
	
	public Map<Integer, String> getArtista(String role){
		String sql = "select artists.artist_id as id, name from artists inner join authorship on"
				+ " artists.artist_id= authorship.artist_id where authorship.role = ? " ;
		
		Map<Integer,String> mappaArtisti = new TreeMap<Integer,String>();
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery() ;
			st.setString(1, role);
			
			while(res.next()) {
				mappaArtisti.put(res.getInt("id"), res.getString("name"));
				
			}
			return mappaArtisti;
		
		
		}
		catch(SQLException s) {
			s.printStackTrace();
		}
		
		return null;
	}
	
	
	public List<Arco> getArchi(String nome){
		String sql = "select e1.exhibition_id, e1.object_id, e2.object_id, count(e1.exhibition_id) as cnt, a1.`artist_id` as art1, a2.`artist_id` as art2\n" + 
				"from  exhibition_objects e1, exhibition_objects e2, authorship a1, authorship a2\n" + 
				"where e1.exhibition_id = e2.exhibition_id and e1.object_id != e2.object_id and e1.object_id = a1.object_id and e2.object_id = a2.object_id and a1.role=? and a2.role=? \n" + 
				"group by e1.object_id, e2.object_id" ;
		
		List<Arco> archi = new LinkedList<Arco>();
Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery() ;
			st.setString(1, nome);
			
			return archi;
		
	}
		catch(SQLException s) {
			s.printStackTrace();
		}
		return null;
	}
}
