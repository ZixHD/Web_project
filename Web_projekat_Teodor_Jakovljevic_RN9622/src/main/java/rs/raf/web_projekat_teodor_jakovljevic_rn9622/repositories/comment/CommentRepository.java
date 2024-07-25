package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.comment;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Comment;

import java.util.List;

public interface CommentRepository {

    public List<Comment> getComments(Integer id);

    public Comment addComment(Comment comment);

    public void deleteComment(Integer id);
}
