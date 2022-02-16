import { Component, OnInit, ViewChild} from '@angular/core';
import { CityService } from '../services/city.service';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {City} from "../models/city.model";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css']
})
export class CityComponent implements OnInit {
  cities: any | undefined;
  foundCity!: City;
  displayedColumns = ["Id", "Name", "Url"];
  editFormVisible: boolean = false;
  totalElements: number;

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  constructor( private service : CityService) { }

  ngOnInit(): void {
    this.service.getCities(0, 3).subscribe(data => {
      this.cities = data.content;
      this.totalElements = data.totalElements;
    });
  }

  onPageChange(event: PageEvent) {
    const pageNumber = event.pageIndex;
    let pageSize = event.pageSize;
    this.service.getCities(pageNumber, pageSize).subscribe(data => {
      this.cities = data.content;
      this.totalElements = data.totalElements;
    });
  }

  searchCity(name: string) {
    if (name.length > 1) {
      this.service.findByName(name).subscribe(data => {
        this.foundCity = data;
      });
    }
  }

  editCity(f: NgForm) {
    const newCity = {
      id: this.foundCity.id,
      name: f.value.name,
      photo: f.value.url
    }
    this.service.editCity(newCity).subscribe(data => {
      this.foundCity = data;
    });
    this.editFormVisible = false;
  }

  openEditForm() {
    this.editFormVisible = true;
  }
}
