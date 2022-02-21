import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { City } from "../models/city.model";
import { Page } from "../models/page.model";

@Injectable({
  providedIn: 'root'
})
export class CityService {

  constructor(private http : HttpClient) { }

  getCities(page: number, size: number) : Observable<Page<City>> {
    return this.http.get<Page<City>>("/cities/v1", {
      params: new HttpParams()
        .set('page', page)
        .set('size', size)
    })
  }

  findByName(name: string) : Observable<City> {
    return this.http.get("/cities/v1/name/" + name).pipe(map(res => <City>res));
  }

  editCity(city: object) : Observable<City> {
    return this.http.put("cities/v1", city).pipe(map(res => <City>res));
  }

  getById(id: string) : Observable<City> {
    return this.http.get<City>("cities/v1/" + id);
  }

}
