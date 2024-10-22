CREATE UNIQUE INDEX eventos_slug_key ON eventos(slug);
CREATE UNIQUE INDEX participantes_evento_id_email_key ON participantes (id_evento, email);
CREATE UNIQUE INDEX check_ins_atendente_id_key ON check_ins (id_participante);
