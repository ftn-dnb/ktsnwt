import { MatButtonModule, MatCheckboxModule, MatInputModule,
    MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
    MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
    MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
    MatDialogModule
} from '@angular/material';
import { NgModule } from '@angular/core';

@NgModule({
    imports: [MatButtonModule, MatCheckboxModule, MatInputModule,
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
        MatDialogModule, 

    ],
    exports: [MatButtonModule, MatCheckboxModule, MatInputModule,
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
        MatDialogModule,

    ]
})
export class MaterialModule {

}
