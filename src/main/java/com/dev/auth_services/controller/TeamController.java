package com.dev.auth_services.controller;

// Java
import com.dev.auth_services.custom.ResourceNotFoundException;
import com.dev.auth_services.entity.Team;
import com.dev.auth_services.service.AuthService;
import com.dev.auth_services.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import com.dev.auth_services.custom.ApiResponse;


@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private AuthService authService;
    @GetMapping("/members")
    public ResponseEntity<ApiResponse> getAllTeams() {
        try {
            List<Team> teams = teamService.getAllTeams();
            ApiResponse response = new ApiResponse(200, true, "Teams fetched successfully", teams);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(500, false, "Failed to fetch teams: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<ApiResponse> getTeamById(@PathVariable String id) {
        try {
            Team team = teamService.getTeamById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
            ApiResponse response = new ApiResponse(200, true, "Team fetched successfully", team);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse response = new ApiResponse(404, false, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(500, false, "Failed to fetch team: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

     @PostMapping("/add")
    public ResponseEntity<ApiResponse> createTeam(@RequestBody Team team, @RequestHeader("Authorization") String token) {
        try {
             authService.validateToken(token); // Uncomment if using a real auth service
            // Check if the phone number already exists
            if (teamService.isPhoneNumberExists(team.getPhone())) {
                ApiResponse response = new ApiResponse(400, false, "Phone number already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }


            Team createdTeam = teamService.saveTeam(team);
            ApiResponse response = new ApiResponse(200, true, "Team created successfully", createdTeam);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(401, false, "Unauthorized: Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTeam(@PathVariable String id, @RequestBody Team teamDetails, @RequestHeader("Authorization") String token) {
        try {
             authService.validateToken(token); // Uncomment if using a real auth service


            Team updatedTeam = teamService.updateTeam(id, teamDetails);
            ApiResponse response = new ApiResponse(200, true, "Team updated successfully", updatedTeam);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse response = new ApiResponse(404, false, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(401, false, "Unauthorized: Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

     @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteTeam(@PathVariable String id, @RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token); // Uncomment if using a real auth service
            teamService.deleteTeam(id);
            ApiResponse response = new ApiResponse(200, true, "Team deleted successfully");
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ApiResponse response = new ApiResponse(404, false, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(401, false, "Unauthorized: Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}