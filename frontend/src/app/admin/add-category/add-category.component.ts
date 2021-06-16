import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {HttpCategoriesService} from "../../services/HttpCategoriesService";
import {Router} from "@angular/router";

@Component({
    selector: 'app-add-category',
    templateUrl: './add-category.component.html',
    styles: []
})
export class AddCategoryComponent implements OnInit {
    public categoryForm: any;

    constructor(private formBuilder: FormBuilder, private categoriesService: HttpCategoriesService, private router: Router) {
    }

    ngOnInit(): void {
        this.categoryForm = this.formBuilder.group({
            name: ['', Validators.required]
        });
    }

    addCategory() {
        let categoryName = this.categoryForm.get('name').value;
        this.categoriesService.createCategory(categoryName).subscribe(response => {
            if (response.created) {
                this.router.navigate(['/admin/kategorie']);
            }
        });
    }
}
