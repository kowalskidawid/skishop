import {Component, OnInit} from '@angular/core';
import {HttpCategoriesService} from "../../services/HttpCategoriesService";

@Component({
    selector: 'app-categories',
    templateUrl: 'categories.component.html',
    styles: []
})
export class CategoriesComponent implements OnInit {
    public response = {
        count: 0,
        categories: []
    };

    public alertMessage;

    constructor(private categoriesService: HttpCategoriesService) {
    }

    ngOnInit(): void {
        this.getList();
    }

    delete(category) {
        if (category.count > 0) {
            this.alertMessage = "Musisz przenieść produkty z tej kategorii aby ją usunąć";
            return;
        }
        this.categoriesService.delete(category.id).subscribe(response => {
            console.log(response);
            this.getList();
        })
        console.log(category);
    }

    getList() {
        this.categoriesService.getAll().subscribe(response => {
            this.response = response;
        })
    }
}
