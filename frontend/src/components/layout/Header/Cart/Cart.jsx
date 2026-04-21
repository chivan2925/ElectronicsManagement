import { useMemo, useState } from "react";
import { FaTimes } from "react-icons/fa";
import { Link } from "react-router-dom";
import lap1 from "../../../../assets/lap1.png";
import lap2 from "../../../../assets/lap2.png";
import { formatCurrency } from "../../../../data/storefront";
import CartProduct from "./CartProduct";

function Cart({ onClose }) {
  const [cartProducts, setCartProducts] = useState([
    {
      id: 1,
      name: "Acer Nitro 5 RTX 4060",
      price: 25990000,
      quantity: 1,
      image: lap1,
    },
    {
      id: 2,
      name: "Predator X34 OLED cong",
      price: 32990000,
      quantity: 1,
      image: lap2,
    },
    {
      id: 3,
      name: "Kingston Fury Beast 32GB",
      price: 3290000,
      quantity: 2,
      image: lap2,
    },
  ]);

  const total = useMemo(
    () =>
      cartProducts.reduce((sum, item) => sum + item.price * item.quantity, 0),
    [cartProducts],
  );

  const handleDeleteProduct = (id) => {
    setCartProducts((prev) => prev.filter((item) => item.id !== id));
  };

  const handleQuantityChange = (id, quantity) => {
    setCartProducts((prev) =>
      prev.map((item) =>
        item.id === id
          ? {
              ...item,
              quantity,
            }
          : item,
      ),
    );
  };

  return (
    <div
      className="surface-panel flex w-[360px] max-w-[calc(100vw-2rem)] flex-col overflow-hidden rounded-3xl border border-white/[0.1] text-white shadow-[0_20px_70px_rgba(0,0,0,0.45)]"
      onClick={(e) => e.stopPropagation()}
    >
      <div className="flex items-center justify-between border-b border-white/[0.08] px-5 py-4">
        <h4 className="text-base font-semibold">
          Giỏ hàng ({cartProducts.length})
        </h4>
        <button
          type="button"
          onClick={onClose}
          className="text-white/60 transition-colors hover:text-[var(--accent)]"
          aria-label="Đóng giỏ hàng"
        >
          <FaTimes size={18} />
        </button>
      </div>

      <div className="max-h-[380px] flex-1 space-y-3 overflow-y-auto px-5 py-4">
        {cartProducts.length > 0 ? (
          cartProducts.map((item) => (
            <CartProduct
              key={item.id}
              id={item.id}
              name={item.name}
              price={item.price}
              quantity={item.quantity}
              image={item.image}
              onQuantityChange={handleQuantityChange}
              onDelete={handleDeleteProduct}
            />
          ))
        ) : (
          <div className="py-8 text-center text-white/60">
            <p>Giỏ hàng trống</p>
          </div>
        )}
      </div>

      <div className="border-t border-white/[0.08] bg-white/[0.02] px-5 py-4">
        <div className="mb-3 flex items-center justify-between">
          <span className="text-sm text-white/75">Tổng cộng</span>
          <span className="text-xl font-bold text-[var(--accent)]">
            {formatCurrency(total)}
          </span>
        </div>
        <Link
          to="/checkout"
          onClick={onClose}
          className="block w-full rounded-xl bg-[var(--accent)] py-3 text-center font-semibold text-[#07110d] transition hover:brightness-95"
        >
          Thanh toán
        </Link>
      </div>
    </div>
  );
}

export default Cart;
