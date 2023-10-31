package live.learnlynx.api.v1.services;

import live.learnlynx.api.v1.models.Tag;

import java.util.List;
import java.util.UUID;

public interface ITagService {

    public Tag getTagById(UUID id);
    public Tag createTag(String tagName);
    public Tag updateTag(String tagName, UUID tagId);
    public String deleteTag(UUID tagId);

    public List<Tag> getTags();

}
