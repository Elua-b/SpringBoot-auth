package live.learnlynx.api.v1.serviceImpls;

import live.learnlynx.api.v1.exceptions.ResourceNotFoundException;
import live.learnlynx.api.v1.models.Tag;
import live.learnlynx.api.v1.repositories.ITagRepository;
import live.learnlynx.api.v1.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements ITagService {

    private final ITagRepository tagRepository;

    @Override
    public Tag getTagById(UUID id) {
        return this.tagRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Tag","id",id));
    }

    @Override
    public Tag createTag(String tagName) {
        Tag tag = new Tag(tagName);
        return this.tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(String tagName, UUID tagId) {
        Tag tag = this.tagRepository.findById(tagId).orElseThrow(()->new ResourceNotFoundException("Tag","id",tagId));
        tag.setTagName(tagName);
        return this.tagRepository.save(tag);
    }

    @Override
    public String deleteTag(UUID tagId) {
        this.tagRepository.deleteById(tagId);
        return "Tag deleted successfully";
    }

    @Override
    public List<Tag> getTags() {
        return this.tagRepository.findAll();
    }
}
