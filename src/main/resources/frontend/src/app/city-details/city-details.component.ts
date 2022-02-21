import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CityService } from "../services/city.service";
import { City } from "../models/city.model";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-city-details',
  templateUrl: './city-details.component.html',
  styleUrls: ['./city-details.component.css']
})
export class CityDetailsComponent implements OnInit {
  cityId!: string;
  city!: City;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private cityService: CityService
  ) { }

  ngOnInit(): void {
    this.cityId = this.router.url.split('/').pop() as string;
    this.cityService.getById(this.cityId).subscribe(data => {
      this.city = data;
    });
  }

  editCity(f: NgForm) {
    const newCity = {
      id: this.cityId,
      name: f.value.name,
      photo: f.value.url
    }
    this.cityService.editCity(newCity).subscribe(data => {
      this.city = data;
    });
    this.router.navigate([``]);
  }

}
