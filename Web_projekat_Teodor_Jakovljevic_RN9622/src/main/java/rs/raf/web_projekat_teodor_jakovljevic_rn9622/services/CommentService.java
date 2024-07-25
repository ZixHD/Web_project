package rs.raf.web_projekat_teodor_jakovljevic_rn9622.services;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Comment;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.comment.CommentRepository;

import javax.inject.Inject;
import java.util.List;

public class CommentService {
    @Inject
    private CommentRepository commentRepository;

    public List<Comment> getComments(Integer id){ return this.commentRepository.getComments(id); }
    public Comment addComment(Comment comment){ return this.commentRepository.addComment(comment); }
    public void deleteComment(Integer id) { this.commentRepository.deleteComment(id); }
}
