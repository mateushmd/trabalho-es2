import React, { useState } from 'react';
import { profissionalService } from '../services/api';

function ProfissionalList({ profissionais = [], onUpdate }) {
  const [form, setForm] = useState({ nome: '', telefone: '', endereco: '', categoria: '' });

  const deletar = async (id) => {
    await profissionalService.deletar(id);
    if(onUpdate) onUpdate();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      ...form,
      categoria: form.categoria.split(',').map(c => c.trim()).filter(c => c)
    };
    await profissionalService.criar(payload);
    setForm({ nome: '', telefone: '', endereco: '', categoria: '' });
    if(onUpdate) onUpdate();
  };

  return (
    <div className="section-container">
      <h2>Profissionais de Saúde</h2>
      
      <form onSubmit={handleSubmit} className="form-container">
        <input type="text" placeholder="Nome" value={form.nome} onChange={e => setForm({...form, nome: e.target.value})} required />
        <input type="text" placeholder="Telefone" value={form.telefone} onChange={e => setForm({...form, telefone: e.target.value})} />
        <input type="text" placeholder="Endereço" value={form.endereco} onChange={e => setForm({...form, endereco: e.target.value})} />
        <input type="text" placeholder="Categorias (separadas por vírgula)" value={form.categoria} onChange={e => setForm({...form, categoria: e.target.value})} />
        <button type="submit">Adicionar Profissional</button>
      </form>

      <table>
        <thead>
          <tr><th>ID</th><th>Nome</th><th>Telefone</th><th>Endereço</th><th>Categorias</th><th>Ações</th></tr>
        </thead>
        <tbody>
          {profissionais.map(p => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.nome}</td>
              <td>{p.telefone}</td>
              <td>{p.endereco}</td>
              <td>{p.categoria?.join(', ')}</td>
              <td><button className="btn-danger" onClick={() => deletar(p.id)}>Excluir</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ProfissionalList;
