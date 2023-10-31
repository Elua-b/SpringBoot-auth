package live.learnlynx.api.v1.controllers;

import live.learnlynx.api.v1.payload.ApiResponse;
import live.learnlynx.api.v1.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/tags")
public class TagController {

    private final ITagService tagService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTag(String tagName) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toString());
        return ResponseEntity.created(uri).body(new ApiResponse(true, "Tag created successfully", this.tagService.createTag(tagName)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getTags() {
        return ResponseEntity.ok().body(new ApiResponse(true, "Tags fetched successfully", this.tagService.getTags()));
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<ApiResponse> getTagById(@PathVariable UUID tagId) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Tag fetched successfully", this.tagService.getTagById(tagId)));
    }

    @PutMapping("/update/{tagId}")
    public ResponseEntity<ApiResponse> updateTag(@PathVariable UUID tagId, @RequestBody String tagName) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Tag updated successfully", this.tagService.updateTag(tagName, tagId)));
    }

    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable UUID tagId) {
        return ResponseEntity.ok().body(new ApiResponse(true, this.tagService.deleteTag(tagId), null));
    }

}
