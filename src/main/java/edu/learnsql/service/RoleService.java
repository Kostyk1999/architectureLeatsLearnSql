package edu.learnsql.service;

import edu.learnsql.entities.main.Role;
import edu.learnsql.dao.main.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("roleService")
public class RoleService {


    @Autowired
    private RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        roles = roleRepository.findAll();
        return roles;
    }

    public Role findRole(int id) {
        return roleRepository.findOne(id);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void delete(int id) {
        roleRepository.delete(id);

    }

    public Role findRole(String role) {
        // TODO Auto-generated method stub
        return roleRepository.findByRole(role);
    }

}
