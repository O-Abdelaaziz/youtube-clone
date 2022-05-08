import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CommentDto } from 'src/app/models/comment-dto';
import { CommentsService } from 'src/app/services/comments.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css'],
})
export class CommentsComponent implements OnInit {
  @Input()
  videoId: string = '';
  commentsForm: FormGroup;
  commentsDto: CommentDto[] = [];

  constructor(
    private _userService: UserService,
    private _commentService: CommentsService,
    private _matSnackBar: MatSnackBar
  ) {
    this.commentsForm = new FormGroup({
      comment: new FormControl('comment'),
    });
  }

  ngOnInit(): void {
    this.getComments();
  }

  postComment() {
    const comment = this.commentsForm.get('comment')?.value;

    const commentDto = {
      commentText: comment,
      authorId: this._userService.getUserId(),
    };

    this._commentService.postComment(commentDto, this.videoId).subscribe(() => {
      this._matSnackBar.open('Comment Posted Successfully', 'OK');

      this.commentsForm.get('comment')?.reset();
      this.getComments();
    });
  }

  getComments() {
    this._commentService.getAllComments(this.videoId).subscribe((data) => {
      this.commentsDto = data;
    });
  }
}
