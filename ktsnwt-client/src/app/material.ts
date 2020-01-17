import { MatButtonModule, MatCheckboxModule, MatInputModule, 
    MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
    MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
    MatSelectModule, MatDialogModule
} from '@angular/material';
import { NgModule } from '@angular/core';

@NgModule({
    imports: [MatButtonModule, MatCheckboxModule, MatInputModule, 
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDialogModule
    ],
    exports: [MatButtonModule, MatCheckboxModule, MatInputModule, 
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule, MatDialogModule
    ]
})
export class MaterialModule {

}