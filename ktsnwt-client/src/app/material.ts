import { MatButtonModule, MatCheckboxModule, MatInputModule, 
    MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
    MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
    MatSelectModule,
} from '@angular/material';
import { NgModule } from '@angular/core';

@NgModule({
    imports: [MatButtonModule, MatCheckboxModule, MatInputModule, 
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule,
    ],
    exports: [MatButtonModule, MatCheckboxModule, MatInputModule, 
        MatIconModule, MatToolbarModule, MatMenuModule, MatTableModule,
        MatSlideToggleModule, MatCardModule, MatTooltipModule, MatPaginatorModule,
        MatSelectModule,
    ]
})
export class MaterialModule {

}