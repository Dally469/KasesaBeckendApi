package com.dev.auth_services.service;

// Java
import com.dev.auth_services.entity.Team;
import com.dev.auth_services.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(String id) {
        return teamRepository.findById(UUID.fromString(id));
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public void deleteTeam(String id) {
        teamRepository.deleteById(UUID.fromString(id));
    }

    public Team updateTeam(String id, Team teamDetails) {
        Team team = teamRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (teamDetails.getFirstname() != null) {
            team.setFirstname(teamDetails.getFirstname());
        }
        if (teamDetails.getLastname() != null) {
            team.setLastname(teamDetails.getLastname());
        }
        if (teamDetails.getPhone() != null) {
            team.setPhone(teamDetails.getPhone());
        }
        if (teamDetails.getDepartment() != null) {
            team.setDepartment(teamDetails.getDepartment());
        }
        if (teamDetails.getPosition() != null) {
            team.setPosition(teamDetails.getPosition());
        }
        if (teamDetails.getStatus() != null) {
            team.setStatus(teamDetails.getStatus());
        }

        if (teamDetails.getProfile() != null){
            team.setProfile(teamDetails.getProfile());
        }

        return teamRepository.save(team);
    }
    public boolean isPhoneNumberExists(String phone) {
        return teamRepository.existsByPhone(phone);
    }



}