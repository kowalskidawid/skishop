import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {Observable, Observer} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class HttpCategoriesService {
    readonly rootUrl: string = 'http://localhost:8080/api/categories';

    constructor(private http: HttpClient) {
    }

    getAll() :Observable<any> {
        return this.http.get(this.rootUrl);
    }

    delete(categoryId: number) :Observable<any> {
        return this.http.delete(`${this.rootUrl}/${categoryId}`);
    }

    createCategory(categoryName: string): Observable<any> {
        return this.http.post(this.rootUrl, {categoryName: categoryName});
    }

    getById(categoryId: number): Observable<any> {
        return this.http.get(`${this.rootUrl}/${categoryId}`);
    }

    update(categoryId: number, category): Observable<any> {
        return this.http.put(`${this.rootUrl}/${categoryId}`, category);
    }
}
