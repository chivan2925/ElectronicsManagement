import { useMemo, useState } from "react";
import { FaArrowLeft, FaShoppingCart } from "react-icons/fa";
import { Link, useNavigate, useParams } from "react-router-dom";
import { formatCurrency, productCatalog } from "../data/storefront";

const productColorOptions = {
  "nitro-5": ["Đen Carbon", "Xám Graphite", "Xanh Forest"],
  "predator-x34": ["Đen", "Bạc", "Xám Titan"],
  "ram-kingston": ["Đen", "Trắng", "Đỏ"],
  "legion-pro": ["Đen Obsidian", "Xám Stone", "Xanh Deep"],
  "workstation-dock": ["Đen", "Bạc", "Xám"],
  "asus-proart": ["Đen", "Xám", "Bạc"],
  "ssd-990-pro": ["Đen", "Đỏ", "Bạc"],
  "mx-master": ["Đen", "Xám", "Trắng"],
};

function ProductDetail() {
  const { productId } = useParams();
  const navigate = useNavigate();

  const product = useMemo(
    () => productCatalog.find((item) => item.id === productId),
    [productId],
  );

  const colors = product
    ? productColorOptions[product.id] || ["Đen", "Bạc"]
    : [];
  const [selectedColor, setSelectedColor] = useState(colors[0] || "");
  const [quantity, setQuantity] = useState(1);

  if (!product) {
    return (
      <section className="page-shell py-16">
        <div className="surface-panel rounded-[28px] p-8 text-center">
          <h1 className="font-display text-3xl font-semibold text-white">
            Không tìm thấy sản phẩm
          </h1>
          <p className="mt-3 text-white/70">
            Sản phẩm bạn chọn không tồn tại hoặc đã được gỡ khỏi danh mục.
          </p>
          <Link
            to="/"
            className="secondary-button mt-6 inline-flex items-center gap-2"
          >
            <FaArrowLeft className="text-xs" />
            Quay về trang chủ
          </Link>
        </div>
      </section>
    );
  }

  const handleBuyNow = () => {
    navigate("/checkout", {
      state: {
        productId: product.id,
        quantity,
        color: selectedColor,
      },
    });
  };

  const handleAddToCart = () => {
    const cartFromStorage = JSON.parse(
      localStorage.getItem("cartItems") || "[]",
    );

    const existingIndex = cartFromStorage.findIndex(
      (item) => item.id === product.id && item.color === selectedColor,
    );

    if (existingIndex >= 0) {
      cartFromStorage[existingIndex].quantity += quantity;
    } else {
      cartFromStorage.push({
        id: product.id,
        name: product.name,
        price: product.price,
        image: product.imageUrl,
        color: selectedColor,
        quantity,
      });
    }

    localStorage.setItem("cartItems", JSON.stringify(cartFromStorage));
    window.dispatchEvent(new Event("cart-updated"));
    window.alert("Đã thêm sản phẩm vào giỏ hàng");
  };

  return (
    <section className="page-shell py-8 sm:py-12">
      <div className="mb-6">
        <Link
          to="/"
          className="inline-flex items-center gap-2 text-sm text-white/70 transition hover:text-[var(--accent)]"
        >
          <FaArrowLeft className="text-xs" />
          Quay lại danh sách sản phẩm
        </Link>
      </div>

      <article className="surface-panel rounded-[32px] p-5 sm:p-8">
        <div className="grid gap-8 lg:grid-cols-[1.1fr_1fr]">
          <div className="rounded-[24px] border border-white/[0.08] bg-white/[0.03] p-6">
            <img
              src={product.imageUrl}
              alt={product.name}
              className="mx-auto h-full max-h-[420px] w-full object-contain"
            />
          </div>

          <div className="flex flex-col">
            <p className="text-xs uppercase tracking-[0.24em] text-[var(--accent)]">
              {product.badge}
            </p>
            <h1 className="font-display mt-3 text-3xl font-semibold text-white sm:text-4xl">
              {product.name}
            </h1>

            <div className="mt-5 flex items-center gap-3">
              <p className="text-3xl font-bold text-white">
                {formatCurrency(product.price)}
              </p>
              {product.compareAt ? (
                <p className="text-sm text-white/50 line-through">
                  {formatCurrency(product.compareAt)}
                </p>
              ) : null}
            </div>

            <p className="mt-5 text-sm leading-7 text-white/75">
              {product.description}
            </p>

            <div className="mt-6">
              <p className="text-sm font-semibold text-white">Màu sắc</p>
              <div className="mt-3 flex flex-wrap gap-2">
                {colors.map((color) => (
                  <button
                    key={color}
                    type="button"
                    onClick={() => setSelectedColor(color)}
                    className={`rounded-full border px-4 py-2 text-sm transition ${
                      selectedColor === color
                        ? "border-white/40 bg-[rgb(41,48,50)] text-[var(--accent)]"
                        : "border-white/[0.12] bg-white/[0.03] text-white/80 hover:border-white/35"
                    }`}
                  >
                    {color}
                  </button>
                ))}
              </div>
            </div>

            <div className="mt-6">
              <p className="text-sm font-semibold text-white">Số lượng</p>
              <div className="mt-3 inline-flex items-center gap-3 rounded-full border border-white/[0.12] bg-white/[0.03] px-3 py-2">
                <button
                  type="button"
                  onClick={() =>
                    setQuantity((current) => Math.max(1, current - 1))
                  }
                  className="inline-flex h-8 w-8 items-center justify-center rounded-full border border-white/[0.2] text-white/80 transition hover:text-[var(--accent)]"
                  aria-label="Giảm số lượng"
                >
                  -
                </button>
                <span className="min-w-8 text-center text-base font-semibold text-white">
                  {quantity}
                </span>
                <button
                  type="button"
                  onClick={() => setQuantity((current) => current + 1)}
                  className="inline-flex h-8 w-8 items-center justify-center rounded-full border border-white/[0.2] text-white/80 transition hover:text-[var(--accent)]"
                  aria-label="Tăng số lượng"
                >
                  +
                </button>
              </div>
            </div>

            <div className="mt-8 flex flex-wrap gap-3">
              <button
                type="button"
                onClick={handleBuyNow}
                className="primary-button min-w-[180px]"
              >
                Mua ngay
              </button>
              <button
                type="button"
                onClick={handleAddToCart}
                className="secondary-button min-w-[220px]"
              >
                <FaShoppingCart className="text-sm" />
                Thêm vào giỏ hàng
              </button>
            </div>
          </div>
        </div>
      </article>
    </section>
  );
}

export default ProductDetail;
