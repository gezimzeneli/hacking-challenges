import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ScoreService} from "../service/score.service";
import {Score} from "../service/Score";
import {catchError} from "rxjs/operators";
import {of} from "rxjs";
import {ScoreResult} from "../service/ScoreResult";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'hacking-challenge-frontend';

  constructor(private scoreService: ScoreService) {
  }

  myFiles:File [] = [];
  name: string = "";
  scores: Score[] = [];
  loading: boolean = false;
  uploadedScore:number = -1;
  errorMessage:string = ''

  onFileChange(event:any) {
    this.errorMessage = "";
    this.myFiles = [];
    for (var i = 0; i < event.target.files.length; i++) {
      let fileName = event.target.files[i].name;
      if (fileName.startsWith('output') && fileName.startsWith('.txt', 7)){
        this.myFiles.push(event.target.files[i]);
      } else {
        this.errorMessage = "Keep in mind to only upload the output file with the correct structure!";
      }
    }
  }

  submit(){
    if (this.name && this.myFiles.length){
      localStorage.setItem("name", this.name);
      this.loading = true;
      this.errorMessage = "";
      this.scoreService.uploadOutputFiles(this.myFiles, this.name)
        .pipe(
          catchError(err => {
            console.log(err)
            this.loading = false;
            return of({score: -1, errorMessage: err.message} as ScoreResult)
          })
        )
        .subscribe((scoreResult: ScoreResult) => {
          console.log(scoreResult);
          this.loading = false;
          this.uploadedScore = scoreResult.score;
          localStorage.setItem("lastResult", scoreResult.score.toString());
          this.errorMessage = scoreResult.errorMessage;
          this.loadScores();
        })
    }

  }

  ngOnInit(): void {
    let item = localStorage.getItem("name");
    if (item){
      this.name = item;
    }
    item = localStorage.getItem("lastResult");
    if (item){
      this.uploadedScore = +item;
    }
    this.loadScores();
  }

  loadScores():void {
    this.scoreService.getScores().subscribe(value => {
      this.scores = value;
    })
  }
}
