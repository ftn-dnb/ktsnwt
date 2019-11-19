import { LoginComponent } from './components/login/login.component';
import { HOME_PATH, LOGIN_PATH } from './config/router-paths';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: HOME_PATH, component: HomeComponent },
  { path: LOGIN_PATH, component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
