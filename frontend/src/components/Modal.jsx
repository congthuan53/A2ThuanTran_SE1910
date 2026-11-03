// Simple popup dialog used for "Create" and "Update" forms.
// Usage: <Modal title="Them tai khoan" onClose={closeModal}>...form here...</Modal>
function Modal({ title, onClose, children }) {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-box" onClick={(event) => event.stopPropagation()}>
        <h2>{title}</h2>
        {children}
      </div>
    </div>
  );
}

export default Modal;
