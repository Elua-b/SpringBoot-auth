package live.learnlynx.api.v1.controllers;

import live.learnlynx.api.v1.enums.EFileStatus;
import live.learnlynx.api.v1.payload.ApiResponse;
import live.learnlynx.api.v1.services.IFileService;
import live.learnlynx.api.v1.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;


    @GetMapping("/all")
    private ResponseEntity<ApiResponse> getAllFiles() {
        return ResponseEntity.ok().body(new ApiResponse(true, "Files fetched successfully", this.fileService.getAll()));
    }

    @GetMapping("/all-paginated")
    private ResponseEntity<ApiResponse> getAllFilesPaginated(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok().body(new ApiResponse(true, "Files fetched successfully", this.fileService.getAll(pageable)));
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<ApiResponse> deleteFile(@PathVariable(name = "id") UUID fileId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Files fetched successfully", this.fileService.delete(fileId)));
    }

    @GetMapping("/by-status/{status}")
    private ResponseEntity<ApiResponse> getFilesByStatus(
            @PathVariable(name = "status") EFileStatus fileStatus,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok().body(new ApiResponse(true, "Files fetched successfully", this.fileService.getAllByStatus(pageable, fileStatus)));
    }

}
