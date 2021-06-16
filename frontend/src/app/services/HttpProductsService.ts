import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class HttpProductsService {
    readonly rootUrl: string = 'http://localhost:8080/api/products';
    constructor(private http: HttpClient) {
    }

    getLastProducts(page: number, perPage = 20) :Observable<any> {
        return this.http.get(`${this.rootUrl}/${page}/${perPage}`);
    }

    getProductsInCategory(categoryId: number, page: number, perPage = 20) :Observable<any> {
        return this.http.get(`${this.rootUrl}/${categoryId}/${page}/${perPage}`);
    }

    create(category: number, product: any): Observable<any> {
        return this.http.post(`${this.rootUrl}/${category}`, product);
    }

    getById(productId: number): Observable<any> {
        return this.http.get(`${this.rootUrl}/${productId}`);
    }

    update(productId: number, categoryId: number, product: any): Observable<any> {
        return this.http.put(`${this.rootUrl}/${productId}/${categoryId}`, product);
    }

    deleteById(productId: number): Observable<any> {
        return this.http.delete(`${this.rootUrl}/${productId}`);
    }

    search(query: string): Observable<any> {
       return this.http.get(`${this.rootUrl}/search/${query}`);
    }
}
