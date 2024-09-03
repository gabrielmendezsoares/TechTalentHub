import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobListComponent } from './components/job-list/job-list.component';
import { JobDetailComponent } from './components/job-detail/job-detail.component';
import { UserProfileComponent } from './components/profile/user-profile.component';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { JobUpdateComponent } from './components/job-update/job-update.component';
import { JobCreateComponent } from './components/job-create/job-create.component';

const routes: Routes = [
  { path: '', redirectTo: '/job-list', pathMatch: 'full' },
  { path: 'job-list', component: JobListComponent },
  { path: 'job-list/:id', component: JobDetailComponent },
  { path: 'job-create', component: JobCreateComponent, canActivate: [AuthGuard] },
  { path: 'job-update/:id', component: JobUpdateComponent },
  { path: 'profile', component: UserProfileComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '/job-list' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }