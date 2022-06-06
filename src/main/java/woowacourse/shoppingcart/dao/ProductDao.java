package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = ((rs, rowNum) ->
            Product.builder()
                    .id(rs.getLong("id"))
                    .productName(rs.getString("name"))
                    .price(rs.getInt("price"))
                    .stock(rs.getInt("stock"))
                    .imageUrl(rs.getString("image_url"))
                    .build()
    );

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, stock, image_url) VALUES (?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, product.getStock());
            preparedStatement.setString(4, product.getImageUrl());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, stock, image_url FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, productRowMapper, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, stock, image_url FROM product";
        return jdbcTemplate.query(query, productRowMapper);
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
