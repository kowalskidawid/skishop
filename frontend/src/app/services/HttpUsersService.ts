import {EventEmitter, Injectable, Output} from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {Observable, Subject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class HttpUsersService {
    readonly rootUrl: string = 'http://localhost:8080/api/users';
    readonly signInUrl: string = 'http://localhost:8080/api/authorize';
    @Output() public getLoggedUser = new Subject();

    constructor(private http: HttpClient) {
    }

    register(userData) :Observable<any> {
        return this.http.post(this.rootUrl, userData);
    }

    login(userData) :Observable<any> {
        return this.http.post(this.signInUrl, userData);
    }

    storeUserDetails(user: any, token: string) {
        this.getLoggedUser.next(user);
        localStorage.setItem('user_details', JSON.stringify(user));
        localStorage.setItem('jwt_token', token);
    }

    getUserDetails() {
        return JSON.parse(localStorage.getItem('user_details'));
    }

    getJwtToken() {
        let token = localStorage.getItem('jwt_token');
        return `Bearer ${token}`;
    }

    logout() {
        this.getLoggedUser.next(null);
        localStorage.removeItem('jwt_token');
        localStorage.removeItem('user_details');
    }

    isLoggedIn() {
        let isLogged = false;
        if (this.getJwtToken() !== 'Bearer null') {
            isLogged = true;
        }
        return isLogged;
    }

    getAll(): Observable<any> {
        return this.http.get(this.rootUrl, {
            headers: {
                'Authorization': this.getJwtToken()
            }
        });
    }

    changeRule(id: number, rule: any): Observable<any> {
        return this.http.put(`${this.rootUrl}/rule/${id}`, {rule: rule}, {
            headers: {
                'Authorization': this.getJwtToken()
            }
        });
    }

    delete(id: number) {
        return this.http.delete(`${this.rootUrl}/${id}`, {
            headers: {
                'Authorization': this.getJwtToken()
            }
        });
    }
}
