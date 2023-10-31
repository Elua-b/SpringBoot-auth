package live.learnlynx.api.v1.controllers;

import live.learnlynx.api.v1.dtos.CreateLessonDTO;
import live.learnlynx.api.v1.dtos.DeleteLessonMultimediaDTO;
import live.learnlynx.api.v1.dtos.UpdateLessonDTO;
import live.learnlynx.api.v1.fileHandling.File;
import live.learnlynx.api.v1.payload.ApiResponse;
import live.learnlynx.api.v1.services.IFileService;
import live.learnlynx.api.v1.services.ILessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lesson")
public class LessonController {

    private final ILessonService lessonService;
    private final IFileService fileService;

    @Value("${uploads.directory.lesson_images}")
    private String lessonMultimediaUploadDirectory;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createLesson(@RequestBody CreateLessonDTO dto, @RequestParam(name = "files") List<MultipartFile> lessonMultimediaFiles) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        List<File> lessonMultimedia = lessonMultimediaFiles.stream().map(media -> this.fileService.create(media, lessonMultimediaUploadDirectory)).collect(Collectors.toList());
        return ResponseEntity.created(uri).body(new ApiResponse(true, "Lesson created successfully", this.lessonService.createLesson(dto, lessonMultimedia)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateLesson(@PathVariable(name = "id") UUID lessonId, @RequestBody UpdateLessonDTO dto) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Lesson updated successfully", this.lessonService.updateLesson(lessonId, dto)));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ApiResponse> getLessonsByCourse(@PathVariable(name = "id") UUID courseId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Lessons fetched successfully", this.lessonService.getLessonsByCourse(courseId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getLessonById(@PathVariable(name = "id") UUID lessonId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Lesson fetched successfully", this.lessonService.getLessonById(lessonId)));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<ApiResponse> deleteLesson(@PathVariable(name = "id") UUID lessonId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Lesson deleted successfully", this.lessonService.deleteLesson(lessonId)));
    }

    @DeleteMapping("/delete-multimedia")
    private ResponseEntity<ApiResponse> deleteLessonMultimedia(@RequestBody DeleteLessonMultimediaDTO dto) {
        File file = this.fileService.getById(dto.getFileId());
        return ResponseEntity.ok().body(new ApiResponse(true, "Multimedia deleted successfully", this.lessonService.deleteLessonMultimedia(dto.getLessonId(), file)));
    }

    @PostMapping("/add-multimedia")
    private ResponseEntity<ApiResponse> addLessonMultimedia(@RequestBody UUID lessonId, @RequestParam("file") MultipartFile lessonMultimedia) {
        File file = this.fileService.create(lessonMultimedia, lessonMultimediaUploadDirectory);
        return ResponseEntity.ok().body(new ApiResponse(true, "Multimedia added successfully", this.lessonService.addLessonMultimedia(lessonId, file)));
    }

}
