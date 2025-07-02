import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environments";

interface StringResponse {
  content: string,
}
@Injectable({
  providedIn: "root"
})
export class WebApiService {


  constructor(private http: HttpClient) {
  }

  public getUserInfo(): Observable<StringResponse> {
    return this.http.get<StringResponse>(`${environment.apiUrl}/userInfo1`, {
      responseType: 'json'
    });
  }

}
