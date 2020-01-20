import { MatButtonModule, MatCheckboxModule, MatInputModule,
    MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
    MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
    MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
} from '@angular/material';
import { NgModule } from '@angular/core';

@NgModule({
    imports: [MatButtonModule, MatCheckboxModule, MatInputModule,
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
    ],
    exports: [MatButtonModule, MatCheckboxModule, MatInputModule,
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule
    ]
})
export class MaterialModule {

}
