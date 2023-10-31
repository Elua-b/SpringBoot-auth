package live.learnlynx.api.v1.controllers;

import live.learnlynx.api.v1.dtos.CreateEnrollmentDTO;
import live.learnlynx.api.v1.payload.ApiResponse;
import live.learnlynx.api.v1.services.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEnrollment(@RequestBody CreateEnrollmentDTO dto) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(new ApiResponse(true, "Enrolled successfully", this.enrollmentService.createEnrollment(dto)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllEnrollments(){
        return ResponseEntity.ok().body(new ApiResponse(true, "Enrollments fetched successfully", this.enrollmentService.getAllEnrollments()));
    }

    @GetMapping("/mine")
    public ResponseEntity<ApiResponse> getAllEnrollmentsForLoggedInUser(){
        return ResponseEntity.ok().body(new ApiResponse(true, "Enrollments fetched successfully", this.enrollmentService.getAllEnrollmentsForLoggedInUser()));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getAllEnrollmentsByUserId(@PathVariable UUID id){
        return ResponseEntity.ok().body(new ApiResponse(true, "Enrollments fetched successfully", this.enrollmentService.getAllEnrollmentsByUserId(id)));
    }

}
