import { AcudienteModel } from "./acudiente.model";
import { CursoModel } from "./curso.model";

export interface EstudianteModel {
  id: number;
  name: string;
  lastName: string;
  email: string;
  courseDTO: CursoModel;
  attendant: AcudienteModel;
}
