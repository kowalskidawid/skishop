import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {HttpUsersService} from "./HttpUsersService";

@Injectable({
    providedIn: 'root'
})
export class HttpOrdersService {
    readonly rootUrl: string = 'http://localhost:8080/api/orders';

    constructor(private http: HttpClient, private usersService: HttpUsersService) {
    }

    getAll(): Observable<any> {
        return this.http.get(this.rootUrl);
    }

    create(products: any): Observable<any> {
        return this.http.post(this.rootUrl, products, {
            headers: {
                'Authorization': this.usersService.getJwtToken()
            }
        });
    }

    updateStatus(id: number, status: string): Observable<any> {
        return this.http.put(`${this.rootUrl}/${id}`, {status: status}, {
            headers: {
                'Authorization': this.usersService.getJwtToken()
            }
        });
    }
}
