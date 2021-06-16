import {Component, OnInit} from '@angular/core';
import {HttpCategoriesService} from "../services/HttpCategoriesService";

@Component({
    selector: 'app-categories',
    templateUrl: 'categories.component.html',
    styles: []
})
export class CategoriesComponent implements OnInit {
    public categories;

    constructor(private categoriesService: HttpCategoriesService) {
    }

    ngOnInit(): void {
        this.categoriesService.getAll().subscribe(response => {
            if (response.count) {
                this.categories = response.categories;
            }
        });
    }

    getThumbnailPath(path) {
        if (path == null) {
            path = '/assets/default.jpeg';
        }

        return path;
    }
}
