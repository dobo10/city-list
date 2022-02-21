import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CityComponent } from "./city/city.component";
import { CityDetailsComponent } from "./city-details/city-details.component";

const routes: Routes = [
  { path: "", component: CityComponent, pathMatch: "full"  },
  { path: "details/:id", component: CityDetailsComponent, pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
