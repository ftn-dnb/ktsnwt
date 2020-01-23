import { MatButtonModule, MatCheckboxModule, MatInputModule,
    MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
    MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
    MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
    MatDialogModule, MatListModule, MatTabsModule,
} from '@angular/material';
import { NgModule } from '@angular/core';

@NgModule({
    imports: [MatButtonModule, MatCheckboxModule, MatInputModule,
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
        MatDialogModule, MatListModule, MatTabsModule,

    ],
    exports: [MatButtonModule, MatCheckboxModule, MatInputModule,
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatAutocompleteModule,
        MatDialogModule, MatListModule, MatTabsModule,

    ]
})
export class MaterialModule {

}
