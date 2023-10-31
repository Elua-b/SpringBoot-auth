package live.learnlynx.api.v1.controllers;

import live.learnlynx.api.v1.dtos.CreateCourseDTO;
import live.learnlynx.api.v1.dtos.UpdateCourseDTO;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.payload.ApiResponse;
import live.learnlynx.api.v1.services.ICourseService;
import live.learnlynx.api.v1.services.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {

    private final IFileService fileService;
    private final ICourseService courseService;

    @Value("${uploads.directory.course_images}")
    private String courseImagesUploadDirectory;

    @Value("${uploads.directory.course_intro_videos}")
    private String courseIntroductoryVideosUploadDirectory;

    @PreAuthorize("hasRole('LECTURER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CreateCourseDTO dto, @RequestParam("courseImage") MultipartFile courseImageFile, @RequestParam("courseIntroductoryVideo") MultipartFile courseIntroductoryVideoFile) {
        File courseImage = this.fileService.create(courseImageFile, courseImagesUploadDirectory);
        File courseIntroductoryVideo = this.fileService.create(courseIntroductoryVideoFile, courseIntroductoryVideosUploadDirectory);
        URI uri = URI.create(ServletUriComponentsBuilder.fromPath("/api/v1/course/create").toString());
        return ResponseEntity.created(uri).body(new ApiResponse(true, "Course created successfully", this.courseService.createCourse(dto, courseImage, courseIntroductoryVideo)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable(name = "id") UUID courseId, @RequestBody UpdateCourseDTO dto, @RequestParam(value = "courseImage", required = false) MultipartFile courseImageFile, @RequestParam(value = "courseIntroductoryVideo", required = false) MultipartFile courseIntroductoryVideoFile) {
        File courseImage = null;
        File courseIntroductoryVideo = null;
        if (courseImageFile != null) {
            courseImage = this.fileService.create(courseImageFile, courseImagesUploadDirectory);
        }
        if (courseIntroductoryVideoFile != null) {
            courseIntroductoryVideo = this.fileService.create(courseIntroductoryVideoFile, courseIntroductoryVideosUploadDirectory);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "Course updated successfully", this.courseService.updateCourse(courseId, dto, courseImage, courseIntroductoryVideo)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable(name = "id") UUID courseId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Course fetched successfully", this.courseService.getCourseById(courseId)));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getCourses() {
        return ResponseEntity.ok().body(new ApiResponse(true, "Courses fetched successfully", this.courseService.getCourses()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable(name = "id") UUID courseId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Course deleted successfully", this.courseService.deleteCourse(courseId)));
    }

    @GetMapping("/get-by-instructor/{id}")
    public ResponseEntity<ApiResponse> getCourseByInstructor(@PathVariable(name = "id") UUID instructorId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Courses fetched successfully", this.courseService.getCourseByInstructor(instructorId)));
    }

    @GetMapping("/learners/{id}")
    public ResponseEntity<ApiResponse> getLearnersPursuingCourse(@PathVariable(name = "id") UUID courseId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Learners fetched successfully", this.courseService.getLearnersPursuingCourse(courseId)));
    }
}
