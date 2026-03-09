import Header from "../components/layout/Header/Header";
import ProductSlideShow from "../components/layout/Main/SlideshowParts/ProductSlideShow";
import HomeBanner from "../components/layout/Main/HomeBanner";
import Footer from "../components/layout/Footer/Footer";
import ProductContainer from "../components/layout/Main/Container/ProductContainer";
import CategoryRow from "../components/layout/Main/CategoryRow";

function Home() {
  return (
    <div className="bg-[#f5f5f5] min-h-screen">
      <HomeBanner />
      <CategoryRow />
      <ProductSlideShow />
      <ProductContainer />
    </div>
  );
}


export default Home;
