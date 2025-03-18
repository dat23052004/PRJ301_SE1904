/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ddshop.dao;

import ddshop.dal.DBContext;
import ddshop.model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MY PC
 */
public class UserDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "SELECT * FROM Users";
    private static final String LOGIN = "SELECT * FROM Users WHERE (username=? OR email = ?) AND password=? and status=1";
    private static final String CHECK_USERNAME_DUPLICATE = "SELECT * FROM Users WHERE userName = ? or email = ? and [status] = 1";
    private static final String REGISTER_USER = "insert into Users(firstname, lastname, email, avatar, username, password, address,phone,roleid,status)  values(?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE Users SET firstName = ?, lastName = ?, email = ?, address = ?, phone = ?, avatar = ?, roleid = ? WHERE username = ?";
    private static final String GET_TOTAL_USERS = "select count(*) as Total from Users";
    private static final String GET_USER_BY_NAME = "SELECT * FROM Users WHERE username = ? AND status = 1";
    private static final String DELETE_USER = "UPDATE Users SET status = 0 WHERE id = ?";

    public List<Users> getData() {
        List<Users> list = new ArrayList<>();

        try {
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String avatar = rs.getString("avatar");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int roleId = rs.getInt("roleid");
                boolean status = rs.getBoolean("status");
                Users user = new Users(id, firstName, lastName, email, avatar, userName, password, address, phone, roleId, status);
                list.add(user);
            }
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());
        }
        return list;
    }

    public Users checkLogin(String username, String password) {
        Users user = null;
        try {
            stm = connection.prepareStatement(LOGIN);
            stm.setString(1, username);
            stm.setString(2, username);
            stm.setString(3, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String userNamee = rs.getString("userName");
                String avatar = rs.getString("avatar");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int roleid = rs.getInt("roleID");
                boolean status = rs.getBoolean("status");
                user = new Users(id, firstname, lastname, email, avatar, userNamee, password, address, phone, roleid, status);
            }
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());

        }
        return user;
    }

    public void registerUser(Users user) {
        try {
            stm = connection.prepareStatement(REGISTER_USER);
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getAvatar());
            stm.setString(5, user.getUsername());
            stm.setString(6, user.getPassword());
            stm.setString(7, user.getAddress());
            stm.setString(8, user.getPhone());
            stm.setInt(9, user.getRole());
            stm.setBoolean(10, user.isStatus());
            stm.execute();
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());
        }
    }

    public boolean CheckExistUsername(String username) {
        try {
            stm = connection.prepareStatement(CHECK_USERNAME_DUPLICATE);
            stm.setString(1, username);
            stm.setString(2, username);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());
        }
        return false;
    }

    public Users getUserByName(String userName) {
        Users user = null;
        try {
            stm = connection.prepareStatement(GET_USER_BY_NAME);
            stm.setString(1, userName);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String avatar = rs.getString("avatar");
                String address = rs.getString("address");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                int roleid = rs.getInt("roleID");
                boolean roleID = rs.getBoolean("roleID");
                user = new Users(id, firstname, lastname, email, avatar, userName, password, address, phone, roleid, roleID);

            }
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());
        }
        return user;
    }

    public void updateUser(String firstName, String lastName, String email, String address, String phone, String username, String avatar, int roleId) {
        try {
            stm = connection.prepareStatement(UPDATE_USER);
            stm.setString(1, firstName);
            stm.setString(2, lastName);
            stm.setString(3, email);
            stm.setString(4, address);
            stm.setString(5, phone);
            stm.setString(6, avatar);
            stm.setInt(7, roleId);
            stm.setString(8, username);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        //        UserDAO uList = new UserDAO();
        //        Users u = new Users(0,"Đạt","Đỗ","datdz@gmail.com","view/assets/home/img/users/1.jpg","datdo","1234567","Quận 7","012345678910",2,true);
        //        uList.registerUser(u);
        //        System.out.println(uList.getData().size());
        UserDAO udao = new UserDAO();
        Users user = udao.checkLogin("dat2305", "1234567");
        if (user != null) {
            System.out.println(user.getAvatar());
        } else {
            System.out.println("null");
        }
    }

    public int getTotalUsers() {
        int result = 0;
        try {
            stm = connection.prepareStatement(GET_TOTAL_USERS);
            rs = stm.executeQuery();
            while (rs.next()) {
                result = rs.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());
        }
        return result;
    }

    public void deleteUser(int uid) {
        try {
            stm = connection.prepareStatement(DELETE_USER);
            stm.setInt(1, uid);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("getUser: " + e.getMessage());
        }
    }
}
