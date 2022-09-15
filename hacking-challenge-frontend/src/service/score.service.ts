import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Score} from "./Score";
import {ScoreResult} from "./ScoreResult";

@Injectable({
  providedIn: 'root'
})
export class ScoreService {

  localPath:string = 'http://localhost:5000';
  remotePath:string = 'http://hackingchallenge-env.eba-2xdirj2b.us-east-1.elasticbeanstalk.com';
  path:string='';

  constructor(private httpClient: HttpClient) {
    this.path = this.remotePath;
  }

  public getScores() : Observable<Score[]>{
    return this.httpClient.get<Score[]>(this.path + '/scores')
  }

  public uploadOutputFiles(files :File[], name: string) :Observable<ScoreResult> {
    const formData = new FormData();
    for (var i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }
    formData.append('name' , name)
    return this.httpClient.post<ScoreResult>(this.path + '/uploadFiles', formData);
  }









}
