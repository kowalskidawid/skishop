import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {HttpCategoriesService} from "../../services/HttpCategoriesService";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-edit-category',
    templateUrl: './edit-category.component.html',
    styles: []
})
export class EditCategoryComponent implements OnInit {
    public categoryForm: any;
    public invalidForm = false;
    private categoryId: number;

    constructor(
        private formBuilder: FormBuilder,
        private categoriesService: HttpCategoriesService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
    ) {
    }

    ngOnInit(): void {
        this.categoryId = Number(this.activatedRoute.snapshot.paramMap.get("id"));
        this.categoryForm = this.formBuilder.group({
            name: ['', Validators.required]
        });
        this.categoriesService.getById(this.categoryId).subscribe(response => {
            this.categoryForm.get('name').setValue(response.name);
        });
    }

    editCategory() {
        if (this.categoryForm.valid) {
            this.categoriesService.update(this.categoryId, {categoryName: this.categoryForm.get('name').value}).subscribe(response => {
                console.log(response);
            })
        } else {
            this.invalidForm = true;
        }
    }
}
